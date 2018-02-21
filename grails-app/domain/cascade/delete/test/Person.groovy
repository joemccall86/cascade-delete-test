package cascade.delete.test

class Person {

    String name
    Organization organization

    static belongsTo = [Organization, Team]
    static hasMany = [teams: Team]

    static constraints = {
    }

    def beforeDelete() {

        Team.withSession {
            removeFromAllTeams()
        }

        true
    }

    def removeFromAllTeams() {
        Team.where {
            members {
                id == this.id
            }
        }.each { Team team ->
            if (team.members.contains(this)) {
                team.members.remove(this)
                team.save()
            }
        }
    }
}
