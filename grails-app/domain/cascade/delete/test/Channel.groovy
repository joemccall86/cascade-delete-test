package cascade.delete.test

class Channel {

    String name

    static belongsTo = [organization: Organization]
}
