package com.example.demo.interfaces.postIt;

import com.example.demo.domain.postIt.PostIt;
import com.example.demo.domain.postIt.PostItInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 api 통신에서 사용하는 UserDTO
 **/
public class PostItDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterRequest {

        //TODO userToken 에 대한 검증 필요
        private String userToken;

        @NotEmpty(message = "내용이 비어 있으면 안됩니다.")
        private String content;
        private String status;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterResponse {
        private final Long postItId;
        private final String postItToken;
        private final String userToken;
        private final String content;
        private final PostIt.PostItStatus status;

        public RegisterResponse(PostItInfo.Main postItInfo) {
            this.postItId = postItInfo.getPostItId();
            this.postItToken = postItInfo.getPostItToken();
            this.userToken = postItInfo.getUserToken();
            this.content = postItInfo.getContent();
            this.status = postItInfo.getStatus();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterBatchRequest {

        private List<RegisterRequest> registerRequestList;

    }

    @Getter
    @Setter
    @ToString
    public static class RegisterBatchResponse {

        private List<RegisterResponse> registerResponses;

        public RegisterBatchResponse(List<PostItInfo.Main> postItInfoList) {
            postItInfoList.forEach(RegisterResponse::new);
        }

    }

}
