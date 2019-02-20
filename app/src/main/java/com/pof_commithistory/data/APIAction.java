package com.pof_commithistory.data;

public enum APIAction {
    RequestCommitHistory;

    /**
     * @return String for api action
     */
    @Override
    public String toString() {
        switch (this) {
            case RequestCommitHistory:
                return "requestCommitHistory";
        }
        return "";
    }
}
