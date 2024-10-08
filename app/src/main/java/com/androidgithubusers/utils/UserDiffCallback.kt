import androidx.recyclerview.widget.DiffUtil
import com.androidgithubusers.data.UserEntity

/**
 * UserDiffCallback is a DiffUtil.ItemCallback implementation for UserEntity objects.
 *
 * This class is used by the RecyclerView adapter to efficiently update the list of users
 * when changes occur. It determines whether two UserEntity objects represent the same item
 * and whether their contents are the same.
 */
class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
    /**
     * Checks if two UserEntity objects represent the same item.
     *
     * @param oldItem The old UserEntity object.
     * @param newItem The new UserEntity object.
     * @return True if the items represent the same user (based on login), false otherwise.
     */
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.login == newItem.login
    }

    /**
     * Checks if the contents of two UserEntity objects are the same.
     *
     * @param oldItem The old UserEntity object.
     * @param newItem The new UserEntity object.
     * @return True if the contents of the items are the same, false otherwise.
     */
    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem == newItem
    }
}
