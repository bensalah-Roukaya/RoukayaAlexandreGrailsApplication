package roukayaalexandregrailsapplication

class BootStrap {

    def init = { servletContext ->
        def userInstance = new User (
                username: "Roukaya",
                password: "password",
                thumbnail: new Illustration(filename: "user1.jpg"))

        def userInstance1 = new User (
                username: "Alexandre",
                password: "password1",
                thumbnail: new Illustration(filename: "user2.jpg"))

        (1..5).each  {
            userInstance.addToAnnonces(
                    new Annonce(
                            title: "title"+ it ,
                            description: "description",
                            validTill: new Date(),
                            state: Boolean.TRUE)
                            .addToIllustrations(new Illustration(filename: 'filename'))
                            .addToIllustrations(new Illustration(filename: 'filename_2'))
                            .addToIllustrations(new Illustration(filename: 'filename_3')))
        }

        userInstance.save(flush: true, failOnError: true)
    }
    def destroy = {}
}
