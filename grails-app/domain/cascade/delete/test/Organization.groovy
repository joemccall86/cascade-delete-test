package cascade.delete.test

class Organization {

    String name

    static hasMany = [
            users: Person,
            channels: Channel
    ]
}
