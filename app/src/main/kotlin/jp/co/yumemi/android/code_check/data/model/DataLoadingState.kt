package jp.co.yumemi.android.code_check.data.model

/**
 * データの読み込み状態を表す
 */
sealed class DataLoadingState {
    /** 初回表示時 */
    data object Initial : DataLoadingState()

    /** 読み込み中 */
    data object InProgress : DataLoadingState()

    /** 読み込み成功 */
    data object Success : DataLoadingState()

    /** 読み込み失敗 */
    data class Failure(val throwable: Throwable) : DataLoadingState()
}
