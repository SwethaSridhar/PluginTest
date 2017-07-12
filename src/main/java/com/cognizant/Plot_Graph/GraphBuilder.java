package com.cognizant.Plot_Graph;
import hudson.Launcher;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;

import java.io.IOException;

public class GraphBuilder extends Publisher {

    private String FilePath;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public GraphBuilder(String FilePath) {
        this.FilePath = FilePath;
    }

    /**
     * We'll use this from the {@code config.jelly}.
     */
    public String getFilePath() {
		return FilePath;
	}
    BuildListener listener;
    @Override
	 public Action getProjectAction(AbstractProject<?,?> project){
	     System.out.println("inside project action"); 
		 return new ProjectAction(project,listener,FilePath);
	    }
    @Override
    public boolean perform(AbstractBuild<?, ?> build,Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
    	EnvVars env = build.getEnvironment(listener);
    	String jenkins_home = env.get("JENKINS_HOME");
    	//listener.getLogger().println("File path in perform class : "+jenkins_home+"/"+FilePath);
		return true;
    }

    

	// Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}
   
    
    
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
     
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        
        public DescriptorImpl() {
            load();
        }
        
       
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Docker Security Scanner Trend Reports";
        }

      

        
    }

    @Override
	public BuildStepMonitor getRequiredMonitorService() {
		// TODO Auto-generated method stub
		return BuildStepMonitor.NONE;
	}
}

