package cascade.delete.test

class Person {

    String name
    Channel selectedChannel

    static belongsTo = [organization: Organization]
}
