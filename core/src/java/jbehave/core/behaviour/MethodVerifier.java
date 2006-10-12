package jbehave.core.behaviour;

import jbehave.core.listener.BehaviourListener;
import jbehave.core.mock.Constraint;


class MethodVerifier implements MethodHandler{

    private final BehaviourVerifier verifier;
    private final BehaviourListener listener;

    public MethodVerifier(BehaviourVerifier verifier, BehaviourListener listener) {
        this.verifier = verifier;
        this.listener = listener;
    }

    public void handleClass(BehaviourClass behaviourClass) {
        behaviourClass.verifyTo(listener);
    }

    public void handleMethod(BehaviourMethod behaviourMethod) {
        verifier.verifyBehaviour(behaviourMethod);
    }
}
