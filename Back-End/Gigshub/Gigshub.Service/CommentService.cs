using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface ICommentSerivce
    {
        IEnumerable<Comment> GetByEventId(long eventId);
        void Create(Comment comment);
        void Save();
    }

    public class CommentService
    {
        #region Properties

        private readonly ICommentRepository commentRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public CommentService(ICommentRepository commentRepository,
            IUnitOfWork unitOfWork)
        {
            this.commentRepository = commentRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ICommentService Members

        public IEnumerable<Comment> GetByEventId(long eventId)
        {
            return commentRepository.GetMany(k => k.EventId == eventId);
        }

        public void Create(Comment comment)
        {
            commentRepository.Add(comment);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
