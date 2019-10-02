package roukayaalexandregrailsapplication

class Illustration {
    String filename

    static constraints = {
        filename blank: false, nullable: false
    }

    @Override
    String toString() {
        return filename
    }
}
