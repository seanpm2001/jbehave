package org.jbehave.scenario.reporters;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.jbehave.scenario.definition.Blurb;
import org.junit.Test;
import org.mockito.InOrder;

public class PassSilentlyDecoratorBehaviour {

    @Test
    public void shouldSwallowOutputFromPassingScenarios() {
        ScenarioReporter delegate = mock(ScenarioReporter.class);
        PassSilentlyDecorator decorator = new PassSilentlyDecorator(delegate);
        IllegalArgumentException anException = new IllegalArgumentException();
        Blurb blurb = new Blurb("Some blurb");
        
        decorator.beforeStory(blurb);
        decorator.beforeScenario("My scenario 1");
        decorator.successful("Given step 1.1");
        decorator.successful("When step 1.2");
        decorator.successful("Then step 1.3");
        decorator.afterScenario();
        
        decorator.beforeScenario("My scenario 2");
        decorator.successful("Given step 2.1");
        decorator.pending("When step 2.2");
        decorator.notPerformed("Then step 2.3");
        decorator.afterScenario();
        
        decorator.beforeScenario("My scenario 3");
        decorator.successful("Given step 3.1");
        decorator.successful("When step 3.2");
        decorator.failed("Then step 3.3", anException);
        decorator.afterScenario();
        
        decorator.beforeScenario("My scenario 4");
        decorator.successful("Given step 4.1");
        decorator.successful("When step 4.2");
        decorator.successful("Then step 4.3");
        decorator.afterScenario();
        decorator.afterStory();
        
        InOrder inOrder = inOrder(delegate);
        
        verify(delegate, never()).beforeScenario("My scenario 1");
        verify(delegate, never()).successful("Given step 1.1");
        verify(delegate, never()).successful("When step 1.2");
        verify(delegate, never()).successful("Then step 1.3");

        verify(delegate, never()).beforeScenario("My scenario 4");
        verify(delegate, never()).successful("Given step 4.1");
        verify(delegate, never()).successful("When step 4.2");
        verify(delegate, never()).successful("Then step 4.3");
        
        inOrder.verify(delegate).beforeStory(blurb);
        inOrder.verify(delegate).beforeScenario("My scenario 2");
        inOrder.verify(delegate).successful("Given step 2.1");
        inOrder.verify(delegate).pending("When step 2.2");
        inOrder.verify(delegate).notPerformed("Then step 2.3");
        inOrder.verify(delegate).afterScenario();
        
        inOrder.verify(delegate).beforeScenario("My scenario 3");
        inOrder.verify(delegate).successful("Given step 3.1");
        inOrder.verify(delegate).successful("When step 3.2");
        inOrder.verify(delegate).failed("Then step 3.3", anException);
        inOrder.verify(delegate).afterScenario();
        inOrder.verify(delegate).afterStory();
        
    }
}
