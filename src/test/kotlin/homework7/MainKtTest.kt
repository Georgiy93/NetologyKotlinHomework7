package ru.netology.homework7

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before


class MainKtTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()

    }


    @Test
    fun add() {
        val like = Likes()

        val post = Post()
        val post3 = post.copy(
            ownerID = 32, data = System.currentTimeMillis(),
            text = "Перерыв 10 минут!", friendsOnly = true,
            likes = like.copy(count = like.count + 141, canPublish = true)
        )
        WallService.add(post3)
        val array = WallService.getAll()
        assertEquals(1, array[0].id)
    }

    @Test
    fun updateTrue() {
        val like = Likes()

        val post = Post()
        val post3 = post.copy(
            text = "Перерыв 10 минут!", friendsOnly = true,
            likes = like.copy(count = like.count + 141, canPublish = true)
        )

        val updatePost = post.copy(
            1,
            isFavorite = true, replyPostID = 2, replyOwnerID = 32,
            canEdit = true,
            text = "Перерыв 20 минут!", friendsOnly = true, postType = "suggest",
            likes = like.copy(count = like.count + 151, canPublish = true)
        )
        WallService.add(post3)
        val result = WallService.update(updatePost)
        assertTrue(result)
    }

    @Test
    fun updateFalse() {
        val like = Likes()

        val post = Post()
        val post3 = post.copy(
            text = "Перерыв 10 минут!", friendsOnly = true,
            likes = like.copy(count = like.count + 141, canPublish = true)
        )
        WallService.add(post3)
        val updatePost = post.copy(
            id = 2,
            isFavorite = true, replyPostID = 2, replyOwnerID = 32,
            canEdit = true,
            text = "Перерыв 20 минут!", friendsOnly = true, postType = "suggest",
            likes = like.copy(count = like.count + 151, canPublish = true)
        )

        val result = WallService.update(updatePost)
        assertFalse(result)
    }

    @Test
    fun createComment() {
        val comment = Comment()
        val like = Likes()
        val post = Post()
        val post1 = post.copy(likes = like.copy(count = like.count + 122, canPublish = false))
        val post2 = post.copy(
            ownerID = 32, data = System.currentTimeMillis(), text = "Перерыв 10 минут!", friendsOnly = true,
            likes = like.copy(count = like.count + 141, canPublish = true)
        )
        WallService.add(post1)
        WallService.add(post2)
        val comment1 = comment.copy(
            text = "Ура! Идем обедать. ",
            id = 1, fromId = 2, date = System.currentTimeMillis()
        )
        // здесь код с вызовом функции, которая должна выкинуть PostNotFoundException
        WallService.createComment(2, comment1)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment = Comment()
        val like = Likes()
        val post = Post()
        val post1 = post.copy(likes = like.copy(count = like.count + 122, canPublish = false))
        val post2 = post.copy(
            ownerID = 32, data = System.currentTimeMillis(), text = "Перерыв 10 минут!", friendsOnly = true,
            likes = like.copy(count = like.count + 141, canPublish = true)
        )
        WallService.add(post1)
        WallService.add(post2)
        val comment1 = comment.copy(
            text = "Ура! Идем обедать. ",
            id = 1, fromId = 2, date = System.currentTimeMillis()
        )
        // здесь код с вызовом функции, которая должна выкинуть PostNotFoundException
        WallService.createComment(5, comment1)
    }
}