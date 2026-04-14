package examples.validation;

public abstract class Validator {
    private Validator nextValidator;
    void setNext(Validator next){
        this.nextValidator = next;
    }
    abstract void check(Context context);
    public Validator linkWith(Validator nextValidator){
        this.nextValidator = nextValidator;
        return nextValidator;
    }
    void validate(Context context){
        check(context);
        if (nextValidator != null){
            nextValidator.validate(context);
        }
    }
}
