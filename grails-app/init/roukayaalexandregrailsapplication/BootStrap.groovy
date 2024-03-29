package roukayaalexandregrailsapplication

class BootStrap {

    def init = { servletContext ->
        def userInstance = new User (
                username: "Roukaya",
                password: "password",
                thumbnail: new Illustration(filename: "user5.jpg"))

        (1..5).each  {
            userInstance.addToAnnonces(
                    new Annonce(
                            title: "title"+ it ,
                            description: "description",
                            validTill: new Date(),
                            state: Boolean.TRUE)
                            .addToIllustrations(new Illustration(filename: 'car1.jpg'))
                            .addToIllustrations(new Illustration(filename: 'car2.jpg'))
                            .addToIllustrations(new Illustration(filename: 'car3.jpg')))
        }

        userInstance.save(flush: true, failOnError: true)

        def userInstance1 = new User (
                username: "Alexandre",
                password: "password1",
                thumbnail: new Illustration(filename: "user2.jpg"))

        userInstance1.addToAnnonces(
                new Annonce(
                        title: "THE title",
                        description: "THE description",
                        validTill: new Date(),
                        state: Boolean.FALSE
                )
                .addToIllustrations(new Illustration(filename: 'Image4.jpg'))
        ).save(flush: true, failOnError: true)
    }
    def destroy = {}
}
