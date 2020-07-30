package io.keepcoding.eh_ho.data

object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()

    fun getTopic(id: String): Topic? = topics.find { it.id == id }

    fun addTopic(title: String, content: String) {
        topics.add(
            Topic(
                title = title,
                content = content
            )
        )
    }

    fun createDummyTopics(count: Int = 20): List<Topic> =
        (0..count).map {
            Topic(
                title = "Title $it",
                content = "Content $it"
            )
        }

}