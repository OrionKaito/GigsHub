using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;

namespace Gigshub.Service
{
    public interface IFollowingService
    {
        IEnumerable<Following> GetByFolloweeId(long followeedId);
        IEnumerable<Following> GetByFollowerId(long followerdId);
        Following Get(long followerId, long followeeId);
        void Create(Following following);
        void Delete(long Id);
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

        public IEnumerable<Following> GetByFolloweeId(long followeedId)
        {
            return followingRepository.GetMany(k => k.FolloweeId == followeedId);
        }

        public IEnumerable<Following> GetByFollowerId(long followerdId)
        {
            return followingRepository.GetMany(k => k.FollowerId == followerdId);
        }

        public Following Get(long followerId, long followeeId)
        {
            return followingRepository.Get(followerId, followeeId);
        }

        public void Create(Following following)
        {
            followingRepository.Add(following);
        }

        public void Delete(long Id)
        {
            var data = followingRepository.GetById(Id);
            followingRepository.Delete(data);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
