package roukayaalexandregrailsapplication

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import java.io.File

class UserController {

    UserService userService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.get(id)
    }

    def create() {
        respond new User(params)
    }

    def save(User user) {

        if (user == null) {
            notFound()
            return
        }

        // récupérer le fichier du formulaire
        def file = request.getFile("file")

        // générer un nom de fichier aléatoire et vérifier qu'il n'existe pas déjà
        final String lexicon = "abcdefghijklmnopqrstuvwxyz12345674890";
        final Random rand = new Random();
        final Set<String> identifiers = new HashSet<String>();

        StringBuilder nomFichier = new StringBuilder();
        while(nomFichier.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                nomFichier.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(nomFichier.toString())) {
                nomFichier = new StringBuilder();
            }
        }

        // Sauvegarder le fichier sur le disque en utilisant le path renseigné dans le fichier de configuration
        file.transferTo(new File(grailsApplication.config.maconfig.assets_path+nomFichier+'.png'))

        // Garder une trace sur le nom du fichier
        user.thumbnail = new Illustration(filename: 'image.png')

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userService.get(id)
    }

    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
