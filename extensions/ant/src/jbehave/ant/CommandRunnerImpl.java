package jbehave.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.Redirector;

import java.io.IOException;

public class CommandRunnerImpl implements CommandRunner {
    public int fork(Task task, String[] command) throws BuildException {
        Redirector redirector = new Redirector(task);
        redirector.createStreams();
        Execute exe = new Execute(redirector.createHandler());
        exe.setAntRun(task.getProject());
        exe.setWorkingDirectory(task.getProject().getBaseDir());
        exe.setCommandline(command);
        try {
            int rc = exe.execute();
            redirector.complete();
            return rc;
        } catch (IOException e) {
            throw new BuildException(e, task.getLocation());
        }
    }

}
