using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;

namespace Gigshub.Service
{
    public interface IFollowingService
    {
        void Create(Following following);
        void Delete(long Id);
        Following Get(long followerId, long followeeId);
        void Save();
    }

    public class FollowingService : IFollowingService
    {
        #region Properties

        private readonly IFollowingRepository followingRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public FollowingService(IFollowingRepository followingRepository,
            IUnitOfWork unitOfWork)
        {
            this.followingRepository = followingRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IFollowingService Members

        public void Create(Following following)
        {
            followingRepository.Add(following);
        }

        public void Delete(long Id)
        {
            var data = followingRepository.GetById(Id);
            followingRepository.Delete(data);
        }

        public Following Get(long followerId, long followeeId)
        {
            return followingRepository.Get(followerId, followeeId);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
