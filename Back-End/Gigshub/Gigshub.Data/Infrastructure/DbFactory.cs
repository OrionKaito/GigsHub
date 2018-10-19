namespace Gigshub.Data.Infrastructure
{
    public class DbFactory : Disposable, IDbFactory
    {
        GigshubEntities dbContext;

        public GigshubEntities Init()
        {
            return dbContext ?? (dbContext = new GigshubEntities());
        }

        protected override void DisposeCore()
        {
            if (dbContext != null)
            {
                dbContext.Dispose();
            }
        }
    }
}
