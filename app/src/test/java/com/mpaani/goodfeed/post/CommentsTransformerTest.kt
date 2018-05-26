package com.mpaani.goodfeed.post

import com.mpaani.goodfeed.core.mock.getFakeComment
import com.mpaani.goodfeed.post.transformer.getCommentViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests the ViewModel transformer for generating Feed View Models.
 */
@RunWith(MockitoJUnitRunner::class)
class FeedTransformerTest {

    @Test
    fun verifyCommentsViewModel() {
        val fakeComment = getFakeComment()
        val commentViewModel = getCommentViewModel(fakeComment)

        assert(commentViewModel.commentName == fakeComment.name)
        assert(commentViewModel.commentBody == fakeComment.body)
        assert(commentViewModel.commentEmail == fakeComment.email)
    }
}
