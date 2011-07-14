package be.rvponp.jenkins;

import java.util.logging.Logger;

import hudson.EnvVars;
import hudson.model.ParameterValue;
import hudson.model.AbstractBuild;

public class WorkspaceValue extends ParameterValue {

	private static final Logger LOG = Logger.getLogger(WorkspaceValue.class.getName());
	
	private String project;
	
	private String workspaceValue;
	
	public String getWorkspaceValue() {
		return workspaceValue;
	}

	public void setWorkspaceValue(String workspaceValue) {
		this.workspaceValue = workspaceValue;
	}

	@Override
	public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
		env.put(getName(), workspaceValue);
	}

	protected WorkspaceValue(String name, String project) {
		super(name);
		this.project = project;
		// TODO Auto-generated constructor stub
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
