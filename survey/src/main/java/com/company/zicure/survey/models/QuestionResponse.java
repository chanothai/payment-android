package com.company.zicure.survey.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 4GRYZ52 on 1/12/2017.
 */

public class QuestionResponse {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("Assessment")
        private Assessment assessment;
        @SerializedName("AssessmentTopic")
        private List<AssessmentTopic> AssessmentTopic;

        public Assessment getAssessment() {
            return assessment;
        }

        public void setAssessment(Assessment assessment) {
            this.assessment = assessment;
        }

        public List<Result.AssessmentTopic> getAssessmentTopic() {
            return AssessmentTopic;
        }

        public void setAssessmentTopic(List<Result.AssessmentTopic> assessmentTopic) {
            AssessmentTopic = assessmentTopic;
        }


        public class AssessmentTopic {
            @SerializedName("id")
            private String id;
            @SerializedName("code")
            private String code;
            @SerializedName("name")
            private String name;
            @SerializedName("assessment_id")
            private String assessmentId;
            @SerializedName("AssessmentQuestion")
            private List<AssessmentQuestion> assessmentQuestion = null;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAssessmentId() {
                return assessmentId;
            }

            public void setAssessmentId(String assessmentId) {
                this.assessmentId = assessmentId;
            }

            public List<AssessmentQuestion> getAssessmentQuestion() {
                return assessmentQuestion;
            }

            public void setAssessmentQuestion(List<AssessmentQuestion> assessmentQuestion) {
                this.assessmentQuestion = assessmentQuestion;
            }

            public class AssessmentQuestion {
                @SerializedName("id")
                private String id;
                @SerializedName("code")
                private String code;
                @SerializedName("name")
                private String name;
                @SerializedName("assessment_topic_id")
                private String amTopicID;
                @SerializedName("AssessmentResult")
                private AssessmentResult amResult = null;
                @SerializedName("AssessmentAnswer")
                private List<AssessmentAnswer> amAnswer = null;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAmTopicID() {
                    return amTopicID;
                }

                public void setAmTopicID(String amTopicID) {
                    this.amTopicID = amTopicID;
                }

                public List<AssessmentAnswer> getAmAnswer() {
                    return amAnswer;
                }

                public void setAmAnswer(List<AssessmentAnswer> amAnswer) {
                    this.amAnswer = amAnswer;
                }

                public AssessmentResult getAmResult() {
                    return amResult;
                }

                public void setAmResult(AssessmentResult amResult) {
                    this.amResult = amResult;
                }


                public class AssessmentResult {
                    @SerializedName("id")
                    private String id;
                    @SerializedName("assessment_question_id")
                    private String amQuestionID;
                    @SerializedName("assessment_answer_id")
                    private String amAnswerID;
                    @SerializedName("value")
                    private String value;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getAmQuestionID() {
                        return amQuestionID;
                    }

                    public void setAmQuestionID(String amQuestionID) {
                        this.amQuestionID = amQuestionID;
                    }

                    public String getAmAnswerID() {
                        return amAnswerID;
                    }

                    public void setAmAnswerID(String amAnswerID) {
                        this.amAnswerID = amAnswerID;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }

                public class AssessmentAnswer {
                    @SerializedName("id")
                    private String id;
                    @SerializedName("code")
                    private String code;
                    @SerializedName("name")
                    private String name;
                    @SerializedName("score")
                    private String score;
                    @SerializedName("assessment_question_id")
                    private String amQuestionID;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }

                    public String getAmQuestionID() {
                        return amQuestionID;
                    }

                    public void setAmQuestionID(String amQuestionID) {
                        this.amQuestionID = amQuestionID;
                    }
                }
            }
        }

        public class Assessment {
            @SerializedName("id")
            private String id;
            @SerializedName("code")
            private String code;
            @SerializedName("name")
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
