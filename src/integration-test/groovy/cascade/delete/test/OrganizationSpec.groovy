package cascade.delete.test


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class OrganizationSpec extends Specification {

    @Unroll
    void "user is removed from team before deletion, runManually = #runManually"() {
        given: 'an existing org'
        Organization.withNewSession {
            def organization = new Organization(name: 'Cyberdyne Systems').save(failOnError: true)

            and: 'a person is added'
            organization.addToUsers(name: 'John Connor').save(failOnError: true)
            organization.save(failOnError: true, flush: true)

            and: 'a new team is added to the org'
            organization.addToTeams(name: 'IT').save(failOnError: true, flush: true)

            and: 'the person is added to the team'
            organization.teams.first().addToMembers(organization.users.first())
            organization.save(failOnError: true, flush: true)
        }

        and: 'the person is deleted'
        def userToDelete = Person.first()
        if (runManually) {
            userToDelete.removeFromAllTeams()
        }
        userToDelete.delete(flush: true)

        expect: 'the team has no users'
        Team.first().members.isEmpty()

        and: 'there are no more users'
        Person.count == 0

        where:
        runManually << [true, false]
    }
}
