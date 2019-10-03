package roukayaalexandregrailsapplication

import grails.converters.JSON
import grails.converters.XML

class ApiController {

    def index() {}

    /** * Will handle GET / PUT / PATCH / DELETE requests*/
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
                break


            case "PATCH":
                break
            case "DELETE":
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406 }



    /** * Will handle GET / POST requests*/
    def annonces() {

        switch(request.getMethod())
        {
            case "GET":

                def annonceInstance = Annonce.list()
                if (!annonceInstance)
                    return response.status = 404
                response.withFormat {
                    xml { render annonceInstance as XML}
                    json { render annonceInstance as JSON }
                }
                break

            case "POST":
                break


            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

}
//curl http://localhost:8091/projet/api/annonce/1 -H "Accept: application/json" -X GET -i :test de annonce

