package be.rvponp.jenkins;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.ParameterValue;
import hudson.model.Hudson;
import hudson.model.ParameterDefinition;
import hudson.model.Project;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

public class WorkspaceParameter extends ParameterDefinition {

	private static final Logger LOG = Logger.getLogger(WorkspaceParameter.class.getName());

	@Extension
	public static final class DescriptorImpl extends ParameterDescriptor {

		@Override
		public String getDisplayName() {
			return "Workspace Parameter";
		}

	}

	private String project;

	private List<String> workspaces = null;

	@DataBoundConstructor
	public WorkspaceParameter(String name, String project) {
		super(name);
		LOG.info("Init WorkspaceParameter for " + name);
		this.project = project;
	}

	@Override
	public ParameterValue createValue(StaplerRequest req) {
		return new WorkspaceValue(getName(), project);
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		WorkspaceValue value = new WorkspaceValue(getName(), project);
		if (jo.has("workspaceValue")) {
			value.setWorkspaceValue(jo.getString("workspaceValue"));
		}
		return value;
	}

	public String getProject() {
		return project;
	}

	@Exported
	public List<String> getWorkspaces() {
		if (workspaces == null) {
			workspaces = new LinkedList<String>();
			for (Project<?, ?> project : Hudson.getInstance().getProjects()) {
				if (project.getName().equals(this.project)) {
					LOG.info("Found " + project);
					try {
						for (FilePath path : project.getWorkspace().list()) {
							workspaces.add(path.getName());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					LOG.info(project + " and " + this.project + " are different");
				}
			}
		}
		return workspaces;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public void setWorkspaces(List<String> workspaces) {
		LOG.info("Set workspaces: " + workspaces);
		this.workspaces = workspaces;
	}
}
