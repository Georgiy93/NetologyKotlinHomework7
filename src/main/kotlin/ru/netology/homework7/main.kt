package ru.netology.homework7


import java.lang.RuntimeException
import java.text.SimpleDateFormat
import kotlin.time.measureTimedValue

data class Post(
    val id: Int = 0,
    val ownerID: Int = 0,
    val createdBy: Int = 0,
    val data: Long = 0,
    val isFavorite: Boolean = false,
    val text: String = "",
    val replyOwnerID: Int = 0,
    val replyPostID: Int = 0,
    val replyPostId: Int = 0,
    val canEdit: Boolean = false,
    val friendsOnly: Boolean = false,
    val postType: String = "",
    val likes: Likes = Likes(),
    val comments: Comment = Comment(),
    val copyright: Copyright = Copyright(),
    val postSource: Post? = null,
    val reposts: Post? = null,
    val views: Post? = null,
    val geo: Post? = null,
    val signerId: Post? = null,
    val copyHistory: Post? = null,
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val isPined: Int = 0,
    val markedAsAds: Boolean = false,
    val donut: Post? = null,
    val postponedId: Int = 0,
    val attachments: Array<Attachment> = emptyArray()
)

data class Audio(
    val id: Int,
    val title: String,
    val artist: String,
    val url: String
)

data class Video(
    val id: Int,
    val title: String,
    val duration: Int
)

data class Photo(
    val id: Int,
    val text: String,
    val width: Int,
    val height: Int
)

data class Doc(
    val id: Int,
    val title: String,
    val url: String
)

data class Sticker(
    val id: Int,
    val url: String,
    val width: Int,
    val height: Int
)

interface Attachment {
    val type: String
}

data class AudioAttachment(val audio: Audio) : Attachment {
    override val type = "audio"
}

data class VideoAttachment(val video: Video) : Attachment {
    override val type = "video"
}

data class PhotoAttachment(val photo: Photo) : Attachment {
    override val type = "photo"
}

data class DocAttachment(val doc: Doc) : Attachment {
    override val type = "doc"
}

data class StickerAttachment(val sticker: Sticker) : Attachment {
    override val type = "sticker"
}


data class Copyright(
    val id: Int = 0,
    val link: String = "",
    val name: String = "",
    val type: String = ""
)

data class Comment(
    val id: Int = 0,
    val fromId: Int = 0,
    val date: Long = 0,
    val text: String = "",
    val donut: Post? = null,
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    val attachments: Array<Attachment> = emptyArray(),
    val parentsStack: Post? = null,
    val thread: Post? = null
)

class PostNotFoundException(text: String) : RuntimeException(text)

data class Likes(

    val count: Int = 0,
    val userLike: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var id = 1
    fun clear() {
        posts = emptyArray()
        id = 1
    }


    fun getAll(): Array<Post> {

        return posts
    }


    //добавление в массив 2я задача
    fun add(post: Post): Post {

        posts += post.copy(id = id++)

        return posts.last()

    }

    fun update(post: Post): Boolean {
        posts.withIndex().forEach {

            when (it.value.id) {

                post.id -> {
                    val old = posts[it.index]
                    posts[it.index] = post.copy(ownerID = old.ownerID, data = old.data)

                    return true
                }


                else -> Unit

            }

        }
        return false
    }

    fun createComment(postId: Int, comment: Comment): Comment {

        posts.withIndex().forEach {

            when (it.value.id) {

                postId -> {
                    comments += comment
                    return comments.last()
                }

                else -> Unit

            }

        }
        return throw PostNotFoundException("Пост с таким id не найден")
    }
}

fun main() {
    val like = Likes()
    val post = Post()
    val comment = Comment()
    var post1 = post.copy(likes = like.copy(count = like.count + 122, canPublish = false))
    var post2 = post.copy(
        ownerID = 32, data = System.currentTimeMillis(), text = "Перерыв 10 минут!", friendsOnly = true,
        likes = like.copy(count = like.count + 141, canPublish = true)
    )
    val updatePost = post.copy(
        2, ownerID = 32, data = System.currentTimeMillis(),
        isFavorite = true, replyPostID = 2, replyOwnerID = 32,
        canEdit = true,
        text = "Перерыв 20 минут!", friendsOnly = true, postType = "suggest",
        likes = like.copy(count = like.count + 151, canPublish = true),
        attachments = arrayOf(
            AudioAttachment(
                Audio(id = 1, title = "SweetDreams", artist = "MarilynManson", url = "https")
            ),
            VideoAttachment(Video(id = 1, title = "SweetDreams", duration = 234))
        )
    )
    val post3 = post.copy(
        text = "Работаем",
        attachments = arrayOf(
            PhotoAttachment(Photo(1, "Cat", 1024, 768)),
            DocAttachment(Doc(1, "Work", "https")),
            StickerAttachment(Sticker(1, "https", 150, 150))
        )
    )
    val comment1 = comment.copy(
        text = "Ура! Идем обедать. ",
        id = 1, fromId = 2, date = System.currentTimeMillis()
    )
    WallService.add(post1)
    WallService.add(post2)
    WallService.add(post3)
    val array = WallService.getAll()
    WallService.update(updatePost)
    WallService.createComment(2, comment1)
    for (post in array) {
        var dataFormat = SimpleDateFormat("Дата поста: dd:MM:yy, время поста:  HH:mm:ss")
        var data = dataFormat.format(post.data)
        println(
            post.text + ", Индетификатор поста: " + post.id +
                    ", Индетификатор автора поста: " + post.ownerID +
                    ", $data " + "Количестов лайков: " + post.likes.count +
                    " Вложения " + post.attachments.contentToString() + "\n"
        )
    }

}
