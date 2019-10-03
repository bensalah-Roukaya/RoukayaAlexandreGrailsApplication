package roukayaalexandregrailsapplication

import grails.converters.JSON
import grails.converters.XML

class ApiController {

    // GET / PUT / PATCH / DELETE
    def annonce() {
        switch(request.getMethod())
        {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                response.withFormat {
                    xml { render annonceInstance as XML}
                    json { render annonceInstance as JSON }
                }
                break
            case "PUT":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.Put(params.id)
                if (!annonceInstance)
                    return response.status = 404

                if (request.XML.title){
                    annonceInstance.title = params.title
                    annonceInstance.save()}

                response.withFormat {
                    xml { render annonceInstance as XML}
                    json { render annonceInstance as JSON }
                }
                break
            case "PATCH":
                break
            case "DELETE":
                break
            default:
                return response.status = 405
                break
        }
        response.status = 406
    }

    // GET / POST
    def annonces() {
        switch(request.getMethod())
        {
            case "GET":
                def annoncesList = Annonce.list()
                if (!annoncesList)
                    return response.status = 404
                response.withFormat {
                    xml { render annoncesList as XML}
                    json { render annoncesList as JSON }
                }
                break
            case "POST":

                break
            default:
                return response.status = 405
                break
        }
        response.status = 406
    }
}

