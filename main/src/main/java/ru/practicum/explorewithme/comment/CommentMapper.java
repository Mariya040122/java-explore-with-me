package ru.practicum.explorewithme.comment;

import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.FullCommentDto;
import ru.practicum.explorewithme.comment.dto.NewCommentDto;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.user.UserMapper;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setEvent(EventMapper.toEventShortDto(comment.getEvent()));
        commentDto.setUser(UserMapper.toUserDto(comment.getUser()));
        commentDto.setPostedOn(comment.getPostedOn());
        return commentDto;
    }

    public static Comment fromNewCommentDto(NewCommentDto newCommentDto){
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        return comment;
    }

    public static FullCommentDto toFullCommentDto(Comment comment){
        FullCommentDto commentDto = new FullCommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setEvent(EventMapper.toEventShortDto(comment.getEvent()));
        commentDto.setUser(UserMapper.toUserDto(comment.getUser()));
        commentDto.setPostedOn(comment.getPostedOn());
        commentDto.setDeleted(comment.getDeleted());
        return commentDto;
    }
}
