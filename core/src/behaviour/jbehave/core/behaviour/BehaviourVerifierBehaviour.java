package jbehave.core.behaviour;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

public class BehaviourVerifierBehaviour extends UsingMiniMock {
    public void shouldVerifyBehaviour() throws Exception {
        // given...
        Mock behaviour = mock(Behaviour.class);
        Mock listener = mock(BehaviourListener.class);
        BehaviourVerifier verifier = new BehaviourVerifier((BehaviourListener) listener);
        
        // expect...
        listener.expects("before").with(behaviour);
        behaviour.expects("verifyTo").with(verifier).after(listener, "before");
        listener.expects("after").with(behaviour).after(behaviour, "verifyTo");
        
        // when...
        verifier.verifyBehaviour((Behaviour)behaviour);
        
        // then...
        verifyMocks();
    }
}
