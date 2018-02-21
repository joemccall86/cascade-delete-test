package cascade.delete.test

class Organization {

    String name

    static hasMany = [
            users: Person,
            teams: Team
    ]

    static mapping = {
        users cascade: 'all-delete-orphan'
    }
}
