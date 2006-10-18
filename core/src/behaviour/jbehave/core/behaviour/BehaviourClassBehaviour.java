/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;
import jbehave.core.result.Result;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClassBehaviour extends UsingMiniMock {
    
    private BehaviourVerifier nullVerifier = new BehaviourVerifier(BehaviourListener.NULL);
    
    public static class ClassWithOneBehaviourMethod {
        public void shouldDoOneThing() {}
    }
    
    private Constraint successfulResultFromMethodCalled(final String name) {
        return new Constraint() {
            public boolean matches(Object arg) {
                Result result = (Result)arg;
                return result.succeeded() && result.name().equals(name);
            }
            public String toString() {
                return "successful result from method called " + name;
            }
        };
    }
    
    private Constraint isBehaviourMethodFor(final String name) {
        return new Constraint() {
            public boolean matches(Object arg) {
                BehaviourMethod behaviourMethod = (BehaviourMethod)arg;
                return behaviourMethod != null && behaviourMethod.method().getName().equals(name);
            }
            public String toString() {
                return "behaviour method for " + name;
            }
        };
    }
    
    public void shouldVerifySingleBehaviourMethod() throws Exception {
        // given...
        Mock listener = mock(BehaviourListener.class);
        final Behaviour[] capturedBehaviour = new Behaviour[1]; // the behaviour
        
        BehaviourVerifier verifier = new BehaviourVerifier(null) { // hand-rolled mock for concrete class
            public void verifyBehaviour(Behaviour behaviour) {
                capturedBehaviour[0] = behaviour;
            }
        };
        Behaviour behaviour = new BehaviourClass(ClassWithOneBehaviourMethod.class, verifier);

        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        ensureThat(capturedBehaviour[0], isBehaviourMethodFor("shouldDoOneThing"));
    }
    
    public void shouldCountSingleBehaviourMethod() throws Exception {
        // given...
        Behaviour behaviour = new BehaviourClass(ClassWithOneBehaviourMethod.class, nullVerifier);

        // then...
        ensureThat(behaviour.countBehaviours(), eq(1));
    }
    
    public static class ClassWithTwoBehaviourMethods {
        public void shouldDoOneThing() {}
        public void shouldDoAnotherThing() {}
    }
    
    public void shouldVerifyMultipleBehaviourMethods() throws Exception {
        // given...
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(
                ClassWithTwoBehaviourMethods.class, new BehaviourVerifier((BehaviourListener) listener));
        
        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoOneThing"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoAnotherThing"));
        
        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public void shouldCountMultipleBehaviourMethods() throws Exception {
        // given...
        Behaviour behaviour = new BehaviourClass(ClassWithTwoBehaviourMethods.class, nullVerifier);

        // then...
        ensureThat(behaviour.countBehaviours(), eq(2));
    }

    public void shouldOnlyRunTheOnesSpecified() {
        BehaviourClass behaviour = new BehaviourClass(ClassWithTwoBehaviourMethods.class, "shouldDoOneThing", nullVerifier);
        ensureThat(behaviour.countBehaviours(), eq(1));
    }
    
    public static class NestedBehaviourClass1 {
        public void shouldDoSomething1() {}
    }
    public static class NestedBehaviourClass2 {
        public void shouldDoSomething2() {}
    }
    public static class ClassWithNestedClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {NestedBehaviourClass1.class, NestedBehaviourClass2.class};
        }
    }
    
    public void shouldVerifyNestedBehaviourClasses() throws Exception {
        // given...
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(
                ClassWithNestedClasses.class, new BehaviourVerifier((BehaviourListener) listener));
        
        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething1"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething2"));
        
        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public void shouldCountNestedBehaviourClasses() throws Exception {
        // given...
        Behaviour behaviour = new BehaviourClass(ClassWithNestedClasses.class, nullVerifier);

        // then...
        ensureThat(behaviour.countBehaviours(), eq(2));
    }
    
    public static class ClassWithDeeplyNestedClasses implements Behaviours {
        public Class[] getBehaviours() {
            return new Class[] {ClassWithNestedClasses.class};
        }
    }
    
    public void shouldVerifyDeeplyNestedBehaviourClasses() throws Exception {
        // given...
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(
                ClassWithDeeplyNestedClasses.class, new BehaviourVerifier((BehaviourListener) listener));
        
        // expect...
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething1"));
        listener.expects("gotResult").with(successfulResultFromMethodCalled("shouldDoSomething2"));
        
        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public void shouldCountDeeplyNestedBehaviourMethods() throws Exception {
        // given...
        Behaviour behaviour = new BehaviourClass(ClassWithDeeplyNestedClasses.class, nullVerifier);

        // then...
        ensureThat(behaviour.countBehaviours(), eq(2));
    }
    
    public static class HasNoBehaviourMethods {
        public void dontRunMe() {}
    }
    
    public void shouldIgnorePublicMethodsThatDontStartWithShould() throws Exception {
        // given...
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(HasNoBehaviourMethods.class, nullVerifier);
        
        // expect...
        listener.expects("gotResult").never();
        
        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        verifyMocks();
    }
    
    public static class HasNonPublicBehaviourMethod {
        protected void shouldNotRunMe() {}
    }
    
    public void shouldIgnoreNonPublicMethodsThatStartWithShould() throws Exception {
        // given...
        Mock listener = mock(BehaviourListener.class);
        Behaviour behaviour = new BehaviourClass(HasNonPublicBehaviourMethod.class, nullVerifier);
        
        // expect...
        listener.expects("gotResult").never();
        
        // when...
        behaviour.verifyTo((BehaviourListener) listener);
        
        // then...
        verifyMocks();
    }
}
