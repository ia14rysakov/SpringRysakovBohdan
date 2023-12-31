package com.rys.springrysakovbohdan.config

import com.rys.springrysakovbohdan.model.Petition
import com.rys.springrysakovbohdan.model.User
import com.rys.springrysakovbohdan.model.Vote
import com.rys.springrysakovbohdan.service.PetitionService
import com.rys.springrysakovbohdan.service.UserService
import com.rys.springrysakovbohdan.service.VoteService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoInit {
    @Bean
    fun init(userService: UserService, voteService: VoteService, petitionService: PetitionService): CommandLineRunner {
        return CommandLineRunner {
            userService.deleteAllUsers()
            voteService.deleteAllVotes()
            petitionService.deleteAllPetitions()

            // Create 5 users
            val users = mutableListOf<User>()
            for (i in 1..5) {
                val user = User(
                    login = "user$i",
                    password = "password$i",
                    email = "user$i@example.com"
                )
                users.add(userService.createUser(user))
            }

            // Create 10 petitions
            val petitions = mutableListOf<Petition>()
            for (i in 1..10) {
                val petition = Petition(
                    title = "Petition Title $i",
                    description = "Description for petition $i. Requesting support.",
                    userId = users[(i - 1) % 5].id!!, // Assign petitions to users in a round-robin fashion
                    url = "https://petition-link-$i.com"
                )
                petitions.add(petitionService.createPetition(petition, petition.userId))
            }

            // Create 30 votes
            for (i in 1..30) {
                val vote = Vote(
                    petitionId = petitions[(i - 1) % 10].id!!, // Voting on petitions in a round-robin fashion
                    userId = users[(i - 1) % 5].id!! // Users voting in a round-robin fashion
                )
                voteService.createVote(vote, vote.userId)

            }

            // Update 5 petitions
        }
    }
}
