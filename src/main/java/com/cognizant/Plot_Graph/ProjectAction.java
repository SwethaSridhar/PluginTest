package com.cognizant.Plot_Graph;

import java.io.IOException;
import java.io.Serializable;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.util.ChartUtil;

public class ProjectAction implements Action , Serializable{
	public static final String URL_NAME = "Result";

    public static final int CHART_WIDTH = 500;
    public static final int CHART_HEIGHT = 200;
    public AbstractBuild<?, ?> build;
	BuildListener listener;
    public AbstractProject<?,?> project;

	public static String FilePath;
    
	public ProjectAction(AbstractProject<?, ?> project,
			BuildListener listener,String FilePath) {
		this.build=build;
		this.listener=listener;
		this.FilePath=FilePath;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIconFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrlName() {
		// TODO Auto-generated method stub
		return URL_NAME;
	}
	/**
    *
    * Redirects the index page to the last result.
    *
    * @param request
    *            Stapler request
    * @param response
    *            Stapler response
    * @throws IOException
    *             in case of an error
    */
	/* public void doIndex(final StaplerRequest request, final StaplerResponse response) throws IOException {
	        AbstractBuild<?, ?> build = getLastFinishedBuild();
	        if (build != null) {
	            response.sendRedirect2(String.format("../%d/%s", build.getNumber(), BuildAction.URL_NAME));
	        }else{
	            // Click to the link in menu on the job page before the first build
	            response.sendRedirect2("..");
	        }
	    }*/
	  /**
     * Returns the last finished build.
     *
     * @return the last finished build or <code>null</code> if there is no
     *         such build
     */
   /* public AbstractBuild<?, ?> getLastFinishedBuild() {
        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        System.out.println("inside map");
        while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
            lastBuild = lastBuild.getPreviousBuild();
        }
        return lastBuild;
    }*/

    /**
     * Get build action of the last finished build.
     *
     * @return the build action or null
     */
    /*public BuildAction getLastFinishedBuildAction() {
        AbstractBuild<?, ?> lastBuild = getLastFinishedBuild();
        return (lastBuild != null) ? lastBuild.getAction(BuildAction.class) : null;
    }*/
    /**
     * Display the trend map.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @throws IOException
     *             in case of an error
     */
	@SuppressWarnings("deprecation")
	public void TrendMap(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		/*System.out.println("inside map");
		 AbstractBuild<?, ?> lastBuild = project.getLastBuild();
		System.out.println(lastBuild);
		 while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
	            lastBuild = lastBuild.getPreviousBuild();
	        }*/
		// AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
	      
	      // BuildAction lastAction = lastBuild.getAction(BuildAction.class);
	System.out.println("Create Map");
	        ChartUtil.generateClickableMap(
	                request,
	                response,
	                GraphPoints.buildChart(build,FilePath),
	                CHART_WIDTH,
	                CHART_HEIGHT);
	    }
	 /**
     * Display the trend map.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @throws IOException
     *             in case of an error
     */
		 @SuppressWarnings("deprecation")
		public void doTrend(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
			 System.out.println("inside map");
			/* AbstractBuild<?, ?> lastBuild = project.getLastBuild();
			 while (lastBuild != null && (lastBuild.isBuilding() || lastBuild.getAction(BuildAction.class) == null)) {
		            lastBuild = lastBuild.getPreviousBuild();
		        }
			 lastBuild = lastBuild.getPreviousBuild();
		        BuildAction lastAction = lastBuild.getAction(BuildAction.class);*/
		        ChartUtil.generateGraph(
		                request,
		                response,
		                GraphPoints.buildChart(build,FilePath),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }
		 /**
		     * Display the trend delta graph.
		     *
		     * @param request
		     *            Stapler request
		     * @param response
		     *            Stapler response
		     * @throws IOException
		     *             in case of an error
		 * @throws InterruptedException 
		     */
		 public void doTrendDeltaMap(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		        /*AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
		        SloccountBuildAction lastAction = lastBuild.getAction(SloccountBuildAction.class);*/

		        ChartUtil.generateClickableMap(
		                request,
		                response,
		                GraphPoints.buildChartDelta(build,FilePath),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }

		    /**
		     * Display the trend delta graph.
		     *
		     * @param request
		     *            Stapler request
		     * @param response
		     *            Stapler response
		     * @throws IOException
		     *             in case of an error
		     * @throws InterruptedException 
		     */
		    public void doTrendDelta(final StaplerRequest request, final StaplerResponse response) throws IOException, InterruptedException {
		       /* AbstractBuild<?,?> lastBuild = this.getLastFinishedBuild();
		        SloccountBuildAction lastAction = lastBuild.getAction(SloccountBuildAction.class);*/

		        ChartUtil.generateGraph(
		                request,
		                response,
		                GraphPoints.buildChartDelta(build,FilePath),
		                CHART_WIDTH,
		                CHART_HEIGHT);
		    }
		 
}
