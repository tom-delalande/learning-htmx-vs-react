package util

import kotlinx.html.Entities
import kotlinx.html.FlowContent
import kotlinx.html.HtmlBlockTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import kotlinx.html.consumers.delayed
import kotlinx.html.org.w3c.dom.events.Event
import kotlinx.html.stream.HTMLStreamBuilder
import kotlinx.html.visitAndFinalize

fun <O : Appendable> O.withHTML(prettyPrint: Boolean = true, xhtmlCompatible: Boolean = false): TagConsumer<O> =
    CustomHTMLStreamBuilder(this, prettyPrint, xhtmlCompatible).delayed()

@HtmlTagMarker
inline fun <T, C : TagConsumer<T>> C.with(
    crossinline block: FlowContent.() -> Unit = {},
): T = EMPTY(this).visitAndFinalize(this, block)

@Suppress("unused")
open class EMPTY(override val consumer: TagConsumer<*>) :
    HtmlBlockTag {
    override val attributes: MutableMap<String, String> = mutableMapOf()
    override val attributesEntries: Collection<Map.Entry<String, String>> = mutableListOf()
    override val emptyTag = true
    override val inlineTag = false
    override val namespace = ""
    override val tagName = ""
}

private class CustomHTMLStreamBuilder<out O : Appendable>(
    out: O,
    prettyPrint: Boolean,
    xhtmlCompatible: Boolean,
) :
    TagConsumer<O> {
    private val streamBuilder = HTMLStreamBuilder<O>(out, prettyPrint, xhtmlCompatible)
    override fun finalize(): O {
        return streamBuilder.finalize()
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {
        if (tag is EMPTY) return
        return streamBuilder.onTagAttributeChange(tag, attribute, value)
    }

    override fun onTagComment(content: CharSequence) {
        return streamBuilder.onTagComment(content)
    }

    override fun onTagContent(content: CharSequence) {
        return streamBuilder.onTagContent(content)
    }

    override fun onTagContentEntity(entity: Entities) {
        return streamBuilder.onTagContentEntity(entity)
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        return streamBuilder.onTagContentUnsafe(block)
    }

    override fun onTagEnd(tag: Tag) {
        if (tag is EMPTY) return
        return streamBuilder.onTagEnd(tag)
    }

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {
        if (tag is EMPTY) return
        return streamBuilder.onTagEvent(tag, event, value)
    }

    override fun onTagStart(tag: Tag) {
        if (tag is EMPTY) return
        return streamBuilder.onTagStart(tag)
    }
}
