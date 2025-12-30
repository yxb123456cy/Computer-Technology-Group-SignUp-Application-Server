package com.lemon.computer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 学生报名记录表
 * @TableName registration_record
 */
@TableName(value ="registration_record")
@Data
public class RegistrationRecord implements Serializable {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 报名学生学号，关联user表student_id
     */
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 报名批次ID，关联registration_batch表id
     */
    @TableField(value = "batch_id")
    private Integer batchId;

    /**
     * 报名意向，多个意向用逗号分隔，如：硬件开发,软件开发
     */
    @TableField(value = "intention")
    private String intention;

    /**
     * 个人简介，可空
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 报名状态：0-待审核，1-已通过，2-未通过
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 审核意见，未通过时建议填写
     */
    @TableField(value = "review_opinion")
    private String reviewOpinion;

    /**
     * 审核时间，审核后自动填充
     */
    @TableField(value = "review_time")
    private LocalDateTime reviewTime;

    /**
     * 报名提交时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 最后更新时间，自动更新
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RegistrationRecord other = (RegistrationRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getIntention() == null ? other.getIntention() == null : this.getIntention().equals(other.getIntention()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getReviewOpinion() == null ? other.getReviewOpinion() == null : this.getReviewOpinion().equals(other.getReviewOpinion()))
            && (this.getReviewTime() == null ? other.getReviewTime() == null : this.getReviewTime().equals(other.getReviewTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getIntention() == null) ? 0 : getIntention().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getReviewOpinion() == null) ? 0 : getReviewOpinion().hashCode());
        result = prime * result + ((getReviewTime() == null) ? 0 : getReviewTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", studentId=" + studentId +
                ", batchId=" + batchId +
                ", intention=" + intention +
                ", introduction=" + introduction +
                ", status=" + status +
                ", reviewOpinion=" + reviewOpinion +
                ", reviewTime=" + reviewTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}