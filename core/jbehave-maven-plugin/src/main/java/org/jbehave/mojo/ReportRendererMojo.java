package org.jbehave.mojo;

import java.io.File;
import java.util.Locale;

import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.jbehave.scenario.reporters.FreemarkerReportRenderer;
import org.jbehave.scenario.reporters.ReportRenderer;

/**
 * Maven plugin for rendering JBehave reports. This plugin can be used both in
 * the <code>integration-test</code> phase and the <code>site</code> phase to
 * render reports generated by reporters.
 * 
 * @goal render-reports
 * @phase site
 */
public class ReportRendererMojo extends AbstractMavenReport {

    /**
     * The output directory.
     * 
     * @parameter expression="${project.build.directory}/jbehave-reports"
     * @required
     */
    private File outputDirectory;

    /**
     * Format of rendering output. Defaults to "html".
     * 
     * @parameter expression="html"
     */
    private String format = "html";

    /**
     * <i>Maven Internal</i>: The Doxia Site Renderer.
     * 
     * @component
     */
    private Renderer siteRenderer;

    /**
     * <i>Maven Internal</i>: The Project descriptor.
     * 
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    protected String getOutputDirectory() {
        return outputDirectory.getAbsolutePath();
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Renderer getSiteRenderer() {
        return siteRenderer;
    }

    public void setSiteRenderer(Renderer siteRenderer) {
        this.siteRenderer = siteRenderer;
    }

    protected MavenProject getProject() {
        return project;
    }

    protected void executeReport(Locale locale) throws MavenReportException {
        ReportRenderer reportRenderer = new FreemarkerReportRenderer();
        try {
            getLog().info("Rendering reports in '" + outputDirectory + "' using format '" + format + "'");
            reportRenderer.render(outputDirectory, format);
        } catch (RuntimeException e) {
            String message = "Failed to render reports in outputDirectory " + outputDirectory
                    + " using format " + format;
            getLog().warn(message, e);
            throw new MavenReportException(message, e);
        }
    }

    public String getOutputName() {
        return "jbehave-reports";
    }

    public String getName(Locale locale) {
        return "JBehave Reports";
    }

    public String getDescription(Locale locale) {
        return "JBehave Reports";
    }

}
