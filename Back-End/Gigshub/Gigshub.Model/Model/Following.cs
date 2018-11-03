using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class Following
    {
        public long Id { get; set; }
        public long FollowerId { get; set; }
        [ForeignKey("FollowerId")]
        public virtual Customer Follower { get; set; }

        public long FolloweeId { get; set; }
        [ForeignKey("FolloweeId")]
        public virtual Customer Followee { get; set; }
    }
}
