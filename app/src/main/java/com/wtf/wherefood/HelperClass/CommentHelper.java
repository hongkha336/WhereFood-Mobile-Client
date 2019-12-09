package com.wtf.wherefood.HelperClass;

import com.wtf.wherefood.Model.Comment;
import com.wtf.wherefood.Model.MetaComment;

import java.util.ArrayList;
import java.util.List;

public class CommentHelper {
    public static List<Comment> convertMetaCommentToComment(List<MetaComment> metaCommentList)
    {
        List<Comment> CommentList = new ArrayList<>();
        if(metaCommentList.size() != 0) {
            Comment cmt = null;
            while (metaCommentList.size() > 0) {
                MetaComment tempCmt = metaCommentList.get(0);
                if (cmt == null) {
                    cmt = new Comment(tempCmt.getUserAccount(), tempCmt.getContent(), tempCmt.getCommentID());
                    cmt.setSurveyPoint(tempCmt.getSurveyPoint());
                    cmt.setDatetime_comment(tempCmt.getDatetime_comment());
                    if (tempCmt.getPictureLink() != "" && tempCmt.getPictureLink() != null && tempCmt.getPictureLink() != "null") {
                        cmt.addLink(tempCmt.getPictureLink());

                    }
                    if (metaCommentList.size() > 1) {
                        MetaComment nextcmt = metaCommentList.get(1);
                        if (nextcmt.getCommentID() != cmt.getCommentiD()) {
                            CommentList.add(cmt);
                            cmt = null;
                        }
                    }
                } else {
                    if (cmt.getCommentiD() == tempCmt.getCommentID()) {
                        if (tempCmt.getPictureLink() != "" && tempCmt.getPictureLink() != null && tempCmt.getPictureLink() != "null") {
                            cmt.addLink(tempCmt.getPictureLink());
                        }

                        if (metaCommentList.size() > 1) {
                            MetaComment nextcmt = metaCommentList.get(1);
                            if (nextcmt.getCommentID() != cmt.getCommentiD()) {
                                CommentList.add(cmt);
                                cmt = null;
                            }
                        }

                    }

                }

                metaCommentList.remove(0);

            }
            CommentList.add(cmt);
        }
        return  CommentList;
    }
}
