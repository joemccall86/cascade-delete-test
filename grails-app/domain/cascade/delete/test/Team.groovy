package cascade.delete.test

class Team {

    String name

    static hasMany = [members: Person]
    static belongsTo = [organization: Organization]

    static constraints = {
    }
}
