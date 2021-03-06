﻿using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface IUserNotificationService
    {
        IEnumerable<UserNotifcation> GetByUserId(long userId);
        int GetNumberOfNotification(long userId);
        void Create(UserNotifcation userNotifcation);
        void Save();
    }

    public class UserNotificationService : IUserNotificationService
    {
        #region Properties

        private readonly IUserNotificationRepository userNotificationRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public UserNotificationService(IUserNotificationRepository userNotificationRepository,
            IUnitOfWork unitOfWork)
        {
            this.userNotificationRepository = userNotificationRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IUserNotificationService Members

        public IEnumerable<UserNotifcation> GetByUserId(long userId)
        {
            return userNotificationRepository.GetMany(k => k.CustomerId == userId);
        }

        public int GetNumberOfNotification(long userId)
        {
            return userNotificationRepository.GetMany(k => k.CustomerId == userId).Count();
        }

        public void Create(UserNotifcation userNotifcation)
        {
            userNotificationRepository.Add(userNotifcation);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
