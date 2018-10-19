using System;

namespace Gigshub.Data.Infrastructure
{
    public interface IDbFactory : IDisposable
    {
        GigshubEntities Init();
    }
}
