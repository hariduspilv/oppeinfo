package ee.hitsa.ois.validation;

/**
 * Validation rule groups for given directive type
 */
public interface DirectiveValidation {

    interface Akad extends DirectiveValidation {
    }

    interface Akadk extends DirectiveValidation {
    }

    interface Eksmat extends DirectiveValidation {
    }

    interface Ennist extends DirectiveValidation {
    }

    interface Finm extends DirectiveValidation {
    }

    interface Immat extends DirectiveValidation {
        // immatv uses same rules
    }

    interface Lopet extends DirectiveValidation {
    }

    interface Okava extends DirectiveValidation {
    }

    interface Okoorm extends DirectiveValidation {
    }

    interface Ovorm extends DirectiveValidation {
    }

    interface Valis extends DirectiveValidation {
    }

    interface Stiptoet extends DirectiveValidation {
    }

    interface Stiptoetl extends DirectiveValidation {
    }
}
