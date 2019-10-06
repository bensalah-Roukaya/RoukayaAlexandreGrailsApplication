package roukayaalexandregrailsapplication

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AnnonceController {

    AnnonceService annonceService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond annonceService.list(params), model:[annonceCount: annonceService.count()]
    }

    def show(Long id) {
        respond annonceService.get(id)
    }

    def create() {
        respond new Annonce(params)
    }

    def save(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }


        // récupérer le fichier du formulaire
        def file = request.getFiles("file")

        file.each{
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
            def assets_path
            if (System.properties['os.name'].toLowerCase().contains('windows')) {
                assets_path = grailsApplication.config.maconfig.assets_path_windows
            } else {
                assets_path = grailsApplication.config.maconfig.assets_path_mac
            }
            it.transferTo(new File(assets_path+nomFichier+'.png'))

            // Garder une trace sur le nom du fichier
            annonce.addToIllustrations(new Illustration(filename: nomFichier+'.png'))
        }


        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond annonceService.get(id)
    }

    def update(Annonce annonce) {
        println params
        if (annonce == null) {
            notFound()
            return
        }

        try {
            save(annonce)
        } catch (ValidationException e) {
            println annonce.errors
            respond annonce.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
            }
            '*'{ respond annonce, [status: OK] }
        }
    }

    def deleteIllustration(){
        def illustrationId = params.illustrationId
        def IllustrationInstance = Illustration.get(illustrationId)
        def annonceId = params.annonceId
        def annonceInstance = Annonce.get(annonceId)
        annonceInstance.removeFromIllustrations(IllustrationInstance)
        annonceInstance.save(flush:true)
        IllustrationInstance.delete(flush:true)
        redirect(controller:"annonce",action:"edit",id: annonceInstance.id)
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        annonceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
