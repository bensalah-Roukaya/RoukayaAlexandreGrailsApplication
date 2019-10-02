package roukayaalexandregrailsapplication

class BootStrap {

    def init = { servletContext ->
        def userInstance = new User (
                username: "Roukaya",
                password: "password",
                thumbnail: new Illustration(filename: "user1.jpg"))

        (1..5).each  {
            userInstance.addToAnnonces(
                    new Annonce(
                            title: "title"+ it ,
                            description: "description",
                            validTill: new Date(),
                            state: Boolean.TRUE)
                            .addToIllustration(new Illustration(filename: 'filename'))
                            .addToIllustration(new Illustration(filename: 'filename_2'))
                            .addToIllustration(new Illustration(filename: 'filename_3')))
        }

        userInstance.save(flush: true, failOnError: true)
    }
    def destroy = {}
}
