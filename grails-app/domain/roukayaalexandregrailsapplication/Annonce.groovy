package roukayaalexandregrailsapplication

import com.sun.org.apache.xpath.internal.operations.Bool

class Annonce {

    String title
    String description
    Date dateCreated
    Date validTill
    Boolean state = Boolean.FALSE

    static belongsTo = [author: User]

    static hasMany = [ illustration: Illustration]
    static constraints = {

        title blank: false, nullable: false
        description blank: false, nullable: false
        validTill nullable: false
        illustration nullable: true

    }
    String toString(){
        return title
    }
}
