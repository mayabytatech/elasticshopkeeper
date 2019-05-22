package com.diviso.graeshoppe.client.store.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ReplyDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-05-20T12:06:46.420+05:30[Asia/Kolkata]")
@Document(indexName = "reply")
public class ReplyDTO   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("repliedDate")
  private Instant repliedDate = null;

  @JsonProperty("reply")
  private String reply = null;

  @JsonProperty("reviewId")
  private Long reviewId = null;

  @JsonProperty("userName")
  private String userName = null;

  public ReplyDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ReplyDTO repliedDate(Instant repliedDate) {
    this.repliedDate = repliedDate;
    return this;
  }

  /**
   * Get repliedDate
   * @return repliedDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Instant getRepliedDate() {
    return repliedDate;
  }

  public void setRepliedDate(Instant repliedDate) {
    this.repliedDate = repliedDate;
  }

  public ReplyDTO reply(String reply) {
    this.reply = reply;
    return this;
  }

  /**
   * Get reply
   * @return reply
  **/
  @ApiModelProperty(value = "")


  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public ReplyDTO reviewId(Long reviewId) {
    this.reviewId = reviewId;
    return this;
  }

  /**
   * Get reviewId
   * @return reviewId
  **/
  @ApiModelProperty(value = "")


  public Long getReviewId() {
    return reviewId;
  }

  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }

  public ReplyDTO userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Get userName
   * @return userName
  **/
  @ApiModelProperty(value = "")


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReplyDTO replyDTO = (ReplyDTO) o;
    return Objects.equals(this.id, replyDTO.id) &&
        Objects.equals(this.repliedDate, replyDTO.repliedDate) &&
        Objects.equals(this.reply, replyDTO.reply) &&
        Objects.equals(this.reviewId, replyDTO.reviewId) &&
        Objects.equals(this.userName, replyDTO.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, repliedDate, reply, reviewId, userName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReplyDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    repliedDate: ").append(toIndentedString(repliedDate)).append("\n");
    sb.append("    reply: ").append(toIndentedString(reply)).append("\n");
    sb.append("    reviewId: ").append(toIndentedString(reviewId)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

