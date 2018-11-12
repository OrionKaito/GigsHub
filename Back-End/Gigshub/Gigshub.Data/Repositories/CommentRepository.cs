using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;

namespace Gigshub.Data.Repositories
{
    public class CommentRepository : RepositoryBase<Comment>, ICommentRepository
    {
        public CommentRepository(IDbFactory dbFactory) : base(dbFactory) { }
    }

    public interface ICommentRepository : IRepository<Comment>
    {

    }
}
