package com.example.dymagram

import com.example.dymagram.network.dto.messages_dto.MessageDto
import com.example.dymagram.network.dto.messages_dto.MessageXDto
import com.example.dymagram.network.services.MessagesService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import kotlin.random.Random
import org.mockito.Mockito.`when`
import retrofit2.Response


class UsernamesServiceTest {

    @Mock
    private lateinit var mockMessageCall: Call<List<MessageDto>>

    @Mock
    private lateinit var mockMessageService: MessagesService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getAllMessages should return a not empty list of messages`() {
        // Préparation
        val mockedResponseBody: List<MessageDto> = generateListOfMockedMessages(10, 10)

        // Simulation d'appel réseau
        `when`(mockMessageCall.execute()).thenReturn(Response.success(mockedResponseBody))
        `when`(mockMessageService.getAllMessages()).thenReturn(mockMessageCall)


        // Appel de la méthode getAllMessages
        val response = mockMessageService.getAllMessages().execute()

        // Vérification du résulat
        assertTrue(response.isSuccessful)

        response.body()?.let {
            assertTrue(it.isNotEmpty())
            assertEquals(it.size, 10)
        }
    }





    private fun generateListOfMockedMessages(total: Int, maxMessagesXPerConv: Int): List<MessageDto> {
        val conversations = mutableListOf<MessageDto>()

        for (conv in 0..<total) {
            conversations.add(
                MessageDto(
                    "conv",
                    generateMockedMessagesX(Random.nextInt(maxMessagesXPerConv)),
                    listOf("Toto", "Tata")
                )
            )
        }

        return conversations
    }

    private fun generateMockedMessagesX(size: Int): List<MessageXDto> {
        val messagesX = mutableListOf<MessageXDto>()

        for(i in 0..<size) {
            messagesX.add(
                MessageXDto(
                    "Message$i",
                    "$i",
                    "00:00:00"
                )
            )
        }

        return messagesX
    }

}