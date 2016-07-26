package ml.kanfa.views.fx.app;

import ml.kanfa.utils.verifier.IdGen;
import ml.kanfa.utils.verifier.VerifierInterface;
import ml.kanfa.utils.verifier.WindowOpenedVerifier;

/**
 * @author Kanfa.
 */

public abstract class BaseStage extends ApplicationComponent implements VerifierInterface{

    public BaseStage(){
        super();
        this.setOnCloseRequest(event -> close());
    }

    @Override public int id() {
        return IdGen.getId(this);
    }

    @Override public void addOpened() {
        WindowOpenedVerifier.addOpened(this);
    }

    @Override public void close() {
        WindowOpenedVerifier.dispose(this);
    }
}
