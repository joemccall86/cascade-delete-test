package cascade.delete.test


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class OrganizationSpec extends Specification {

    void "organization deletions cascade"() {
        given: 'an existing org'
        def organization = new Organization(name: 'Cyberdyne Systems').save(failOnError: true)

        and: 'some channels'
        organization.addToChannels(name: 'FOX').save(failOnError: true)

        and: 'the organization is saved'
        organization.save(flush: true)

        and: 'a person is added'
        organization.addToUsers(name: 'John Connor', selectedChannel: Channel.first()).save(failOnError: true)
        organization.save(failOnError: true, flush: true)

        when: 'the organization is deleted'
        // currently triggers
        // org.h2.jdbc.JdbcSQLException: NULL not allowed for column "SELECTED_CHANNEL_ID"
        organization.delete(flush: true)

        then: 'everything is cascaded'
        Person.count == 0
        Channel.count == 0
        Organization.count == 0
    }
}
