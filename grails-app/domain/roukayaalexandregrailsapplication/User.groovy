package roukayaalexandregrailsapplication

class User {
    String username
    String password
    Date dateCreated
    Date lastUpdated
    Illustration thumbnail

    static hasMany = [annonces: Annonce]

    static constraints = {
        username nullable: false, blank: false, size: 5..20
        password password: true, nullable: false, blank: false, size: 8..30
        thumbnail nullable: false
        annonces nullable: true
    }

    @Override
    String toString() {
        return username
    }

    def upload() {
        def file = request.getFile('file')
        if(file.empty) {
            flash.message = "File cannot be empty"
        } else {
            def illustrationInstance = new Illustration()
            illustrationInstance.filename = file.originalFilename
            illustrationInstance.fullPath = grailsApplication.config.uploadFolder + illustrationInstance.filename
            file.transferTo(new File(illustrationInstance.fullPath))
            illustrationInstance.save()
        }
        redirect (action:'list')
    }
}
