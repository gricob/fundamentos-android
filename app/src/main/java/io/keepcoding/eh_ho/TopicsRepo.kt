package io.keepcoding.eh_ho

object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()
    get() {
        if (field.isEmpty())
            field.addAll(createDummyTopics())
        return field
    }

    fun createDummyTopics(count: Int = 10): List<Topic> =
        (0..count).map {
            Topic(
                title = "Title $it",
                content = "Content $it"
            )
        }
}